# Nix / NixOS
### The purely functional Linux distribution

---

# Nix - Package Manager

- Linux / UNIX
- Source / binary model
- Reproducible

---

# Nix - Find package

```
~> nix-env -qaP 'git.*'

nixpkgs.gitAndTools.gitFull          git-2.7.1
nixpkgs.git                          git-minimal-2.7.1
```

---

# Nix - Install package

```
~> nix-env -i -A nixpkgs.gitAndTools.gitFull

~> nix-env -i -A nixpkgs.gitAndTools.gitFull
installing ‘git-2.7.1’
these paths will be fetched (14.73 MiB download, 84.78 MiB unpacked):
  /nix/store/07zkprjj5s6nvl2b6hzmd9wz4li90cnf-perl-Encode-Locale-1.05
  /nix/store/2bvpy3bw05a5mvybay6ph501bmf6s05z-gettext-0.19.7
  /nix/store/2scsgsv8yyi8ilxq1vr8fbgji18dk9cx-serf-1.3.7
  /nix/store/2y9fsf1khqib3himdfdc1vi72idbwnz9-perl-HTTP-Date-6.02

[...]

fetching path ‘/nix/store/d41x72dlj4y9xwcvkzcbdgxad63a3zg4-git-2.7.1’...

*** Downloading ‘https://cache.nixos.org/nar/0h6h1pvjvzw3kh70fv8zqnvrj7g0gcyx6y2hxvr6b2msjmy3p5wq.nar.xz’ to ‘/nix/store/d41x72dlj4y9xwcvkzcbdgxad63a3zg4-git-2.7.1’...
  % Total    % Received % Xferd  Average Speed   Time    Time     Time  Current
                                 Dload  Upload   Total   Spent    Left  Speed
100 4192k  100 4192k    0     0   672k      0  0:00:06  0:00:06 --:--:--  689k

building path(s) ‘/nix/store/2dlf808d6l4afhin8qrr9g9rf78g28vx-user-environment’
created 248 symlinks in user environment
~>
```

---

# Nix - Nix store

```
~> type -p git
git is /home/nequi/.nix-profile/bin/git
~> readlink -e $(!!)
/nix/store/d41x72dlj4y9xwcvkzcbdgxad63a3zg4-git-2.7.1/bin/git
~> echo "${PATH}" | fold -w 40
/home/nequi/.nix-profile/bin:/home/nequi
/.nix-profile/sbin:/usr/local/sbin:/usr/
local/bin:/usr/sbin:/usr/bin:/sbin:/bin:
/usr/games:/usr/local/games:/opt/liquiba
se-3.2.3
```

---

# Nix - Rollback

```
~> nix-env --Rollback
switching from generation 8 to 7
~> nix-env -i -A nixpkgs.gitAndTools.gitFull
installing ‘git-2.7.1’
```

---

# NixOS - Nix-based Linux

- Atomic updates
- Rollback

# NixOS - Installation

```
$ fdisk /dev/sda
$ mount /dev/sda1 /mnt
$ nixos-generate-config --root /mnt
$ curl -O http://.../configuration.nix
$ mv configuration.nix /mnt/etc/nixos/
$ nixos-install
```

---

# NixOS - Configuration

### Demo

<https://github.com/NeQuissimus/DevSetup/blob/master/ux305c.nix>

---

# NixOS - New configuration


Not persistent across reboots
```
~> nixos-rebuild test
```

New default boot entry
```
~> nixos-rebuild switch
```

---

# NixOS - Failed update?

![inline](./nixos-grub.png)

---

# NixOS - Need a VM?

```
~> nixos-rebuild build-vm
~> ./result/bin/run-${HOSTNAME}-vm
```

---

# NixOS - Only install what you actually Need

![inline](./headless.jpg)

---

# NixOps - Deploy clusters

```
{
  webserver =
    { deployment.targetEnv = "ec2";
      deployment.region = "eu-west-1";
      services.httpd.enable = true;
      services.httpd.documentRoot = "/data";
      fileSystems."/data" =
        { fsType = "nfs4";
          device = "fileserver:/"; };
    };

  fileserver =
    { deployment.targetEnv = "ec2";
      deployment.region = "eu-west-1";
      services.nfs.server.enable = true;
      services.nfs.server.exports = "...";
    };
}
```

```
$ nixops create -d simple network.nix
$ nixops deploy -d simple
```

---

# The End

### Questions?
