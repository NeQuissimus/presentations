footer: 2017, Tim Steinbach

# Kubernetes and Jenkins
## A match made in the cloud

---

## Is our Jenkins running in Kubernetes?
### No!
##### (not yet?)

---

## Do we provision dynamic build slaves in Kubernetes?
### No!
##### Not yet

---

## What do we do so far?

- Manifest generation
- Deployment
- Testing
- (poor man’s) Continuous Deployment

---

# Manifest generation

- [Kubernetes design proposal for templating](https://github.com/kubernetes/community/blob/master/contributors/design-proposals/templates.md)
- [ktmpl @ GitHub](https://github.com/InQuicker/ktmpl)
- [CDS Duo template](https://gerrit.internal/gitweb?p=cds/kubernetes.git;a=blob_plain;f=deployments/duo.yaml;h=8673ec7aec45e1fc10da810dcb37433c5910caef;hb=refs/heads/master)

---

# Deployment

- Shared library
- [Generic deployment job](http://jenkins.internal/job/Kubernetes/job/Submit%20deployment/)
- [Example: CDS](https://gerrit.internal/gitweb?p=jenkins/kubernetes.git;a=blob_plain;f=deploy.groovy;h=3ca309795a37a087d73619d47271d04f013423f6;hb=refs/heads/master)

---

# (Integration-)Testing

- Deploy test manifests
- Gather results

---

# Continuous Deployment

- Permanent namespace, CI updates with new manifests every time
