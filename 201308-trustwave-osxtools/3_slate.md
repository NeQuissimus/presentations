# Slate

![Slate logo](img/slate.png)

**Demo**

!SLIDE text-size-80

# Slate

```
alias hyper ctrl;alt;cmd

# Keypad 5 = fullscreen
alias full move screenOriginX;screenOriginY screenSizeX;screenSizeY
bind pad5:${hyper} ${full}
bind 5:${hyper} ${full}

#Keypad 0 = 7/8 screen
alias partial move screenOriginX+screenSizeX/16;screenOriginY+screenSizeY/16 screenSizeX*7/8;screenSizeY*7/8
bind pad0:${hyper} ${partial}
bind 0:${hyper} ${partial}

#Push Bindings
bind right:cmd,ctrl push right
bind left:cmd,ctrl push left
```
