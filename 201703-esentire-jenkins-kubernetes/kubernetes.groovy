#!/usr/bin/groovy
package esentire

public class Kubernetes implements Serializable {
  String ca
  String cert
  String hostname
  String key
  String namespace
  def script

  public Kubernetes(def script, String ca, String cert, String key, String namespace = null, String hostname = null) {
    this.script = script
    this.ca = ca
    this.cert = cert
    this.hostname = hostname ?: "https://kubeapi.dev"
    this.key = key
    this.namespace = namespace ?: "${this.script.env.JOB_NAME}-${this.script.env.BUILD_NUMBER}".replace(" ", "-").replace("/", "_").toLowerCase()
  }

  def cmd() {
    return "kubectl --certificate-authority=\"${this.ca}\" --client-certificate=\"${this.cert}\" --client-key=\"${this.key}\" --server=\"${this.hostname}\""
  }

  def cmdns() {
    return "${cmd()} --namespace=\"${this.namespace}\""
  }

  def createdNamespace() {
    def status = this.script.sh(script: "${cmd()} get namespace ${this.namespace}", returnStatus: true)
    return (status == 0)
  }

  def ensureNamespace() {
    if (!createdNamespace()) {
      this.script.sh "${cmd()} create namespace ${this.namespace}"
    }
  }

  def deleteNamespace() {
    if (createdNamespace()) {
      this.script.sh "${cmd()} delete namespace ${this.namespace}"
    }
  }

  def submitDeployment(String file) {
    ensureNamespace()

    def deploymentName = this.script.sh(returnStdout: true, script: "${cmdns()} create -f ${file} | awk -F \\\" '{ print \$2 }'")

    this.script.waitUntil {
      def done = this.script.sh(returnStatus: true, script: "${cmdns()} rollout status deployment ${deploymentName}")
      return (done == 0)
    }
  }

  def submit(String file) {
    ensureNamespace()
    this.script.sh "${cmdns()} create -f ${file}"
  }
}
