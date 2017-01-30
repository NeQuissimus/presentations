# Types vs tests
### Conditionals are code-smells

---

# Ruby Kafka client

```ruby
def publish(topic, value, key = nil)
  mts = @producer.produce(value, topic: topic, partition: 0)
end
```

- What is the return type? (It depends...)
- Synchronous or asynchronous? (It depends...)
- Does this send the message to Kafka or queue it up? (It depends...)
- What is value allowed to be? Any object?
- What is mts? (nil, always!)
- What happened to key?

None of this is enforced or documented.

---

# A few tests

```ruby
publish(nil, "{}") # Error
publish(".", "{}") # Error
publish("..", "{}") # Error
publish("a"*250, "{}") # Error
publish("show_me_the_$$$", "{}") # Error
publish("goodtopic", MyObject.new([ "lots" "of" "important" "values" ])) # Error
publish("goodtopic", "{}") # Maybe an error, maybe not (library has a queue that may be full)
```

---

# Make it better

```ruby
def publish(topic, value, key = nil)
  if ((topic != nil) && (topic != ".") && (topic != "..")
      && (topic.length < 250) && ((/[a-zA-Z0-9\_\.]/ =~ topic) == 0)
      && (value) && (value.kind_of?(String)))
    @producer.produce(value, topic: topic, partition: 0)
  else
    raise BadValuesError
  end
end
```

---

# Fix return type

```ruby
def publish(topic, value, key = nil)
  if ((topic != nil) && (topic != ".") && (topic != "..")
      && (topic.length < 250) && ((/[a-zA-Z0-9\_\.]/ =~ topic) == 0)
      && (value) && (value.kind_of?(String)))
    begin
      @producer.produce(value, topic: topic, partition: 0)
      return true
    rescue => e
      return false
    end
  else
    return false
  end
end
```

1 line of logic, 10 lines of verification

And we are not even done yet:
- Sync or async?
- Internal queueing or communication with Kafka?
- If the return value is false, why?

---

# Rewind

## What do we want?

- Send a message to Kafka
- Convey all information the code assumes/needs/provides
- Less verification in individual methods

---

# How about this?

(Let's also ignore the partition key)

```scala
type TopicSpec = NonEmpty And MaxSize[W.`249`.T] And MatchesRegex[W.`"[a-zA-Z0-9\\\\.\\\\-_]+"`.T] And Not[Equal[W.`"."`.T]] And Not[Equal[W.`".."`.T]]
type Topic = String Refined TopicSpec
```

```scala
class KafkaClient[V] {
  def valueSerializer: Class[Serializer[V]]
  ...

  def publish(topic: Topic, value: V): Try[Future[RecordMetadata]] = Try {
    val record = new ProducerRecord[K, V](topic.value, value)
    producer.send(record)
  }
}
```

---

# What about it?

- Topic cannot be invalid, ever: No need for verification at runtime
- Value has a valid serializer: No need to verify, limit to String
- Return type contains everything that could happen (I did not throw in an IO monad for simplicity):
  - Try can be a Success or Failure (Failure will catch exceptions and return them)
  - Future signals async operation, can fail
  - RecordMetadata is actually meaningful (compared to nil)

---

# What does this give us?

- Fewer lines of code
- Lower complexity
- Safer
- Faster runtime (no value inspections)
- Types are documentation
- Refined types (TopicSpec) are re-usable
- Fewer unit tests needed
- Faster development (harder to do things wrong, fewer edge-cases)
