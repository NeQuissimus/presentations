# Command-line action!

OSX maintenance scripts

```
sudo periodic daily weekly monthly
```

!SLIDE

# Command-line action!

Login window message

```
sudo defaults write /Library/Preferences/com.apple.loginwindow \
LoginwindowText "Hello world!"
```

!SLIDE

# Command-line action!

Login window picture

```
defaults write /Library/Preferences/com.apple.loginwindow \
DesktopPicture "~/Pictures/Me.png"
```

!SLIDE

# Command-line action!

Rebuild search index

```
sudo mdutil -E /
```

!SLIDE

# Command-line action!

Turn Spotlight off/on

```
sudo mdutil -a -i [on|off]
```

!SLIDE

# Command-line action!

Add empty dock icons as spacers

```
defaults write com.apple.dock persistent-apps -array-add \
'{tile-data={}; tile-type="spacer-tile";}'
killall Dock
```

!SLIDE

# Command-line action!

Hide/show ~/Library folder

```
chflags [nohidden|hidden] ~/Library
```

!SLIDE

# Command-line action!

Headless VMware Fusion

```
/Library/Application\ Support/VMware\ Fusion/vmrun -T fusion \
start /Volumes/VMs/_Hadoop/Hadoop\ Master.vmwarevm/\
Hadoop\ Master.vmx nogui
```

!SLIDE

# Command-line action!

Limit network bandwidth

```
ipfw pipe 1 config bw 100KB
ipfw add 1 pipe 1 src-port any
ipfw delete 00001 # Remove
```

!SLIDE

# Command-line action!

Fix duplicate content menu entries

```
/System/Library/Frameworks/CoreServices.framework/Versions/A/\
Frameworks/LaunchServices.framework/Versions/A/Support/lsregister\
-kill -r -domain local -domain system -domain user
```

!SLIDE

# Command-line action!

Show/empty list of Safari download history

```
sqlite3 \
~/Library/Preferences/com.apple.LaunchServices.QuarantineEventsV2\
'select LSQuarantineDataURLString from LSQuarantineEvent'
```

```
sqlite3 \
~/Library/Preferences/com.apple.LaunchServices.QuarantineEvents*\
'delete from LSQuarantineEvent'
```
