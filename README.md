App Development
Get a Starter Kit
Node.js Express App
Create
Create
Deploy your app
Cloud Foundry
New
OK
Next
Create<br>

Deployment Automation

Name koa

Code git

右上角图标下拉Settings
Access Tokens
write_repository

左边Settings
Repository
Protected Branches
unprotect

git fetch --unshallow
              git remote set-url origin https://chaowen.guo1:KF1d8ff2zyvi7nbkUvTs@us-south.git.cloud.ibm.com/chaowen.guo1/koa.git
              git push -u origin master -f

---
applications:
- instances: 1
  timeout: 180
  buildpack: https://github.com/cloudfoundry/nodejs-buildpack.git#v1.7.24
  name: koa
  disk_quota: 1G
  memory: 128MB
  domain: mybluemix.net
  host: koaa
  env:
    OPTIMIZE_MEMORY: true
