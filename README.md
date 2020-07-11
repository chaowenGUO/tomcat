https://manage.openshift.com/

Administrator create project<br>
Developer Add Database PostgreSQL Instantiate Template<br>
set the values of PostgreSQL Connection Username and PostgreSQL Connection Password

Developer Add from Git

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
