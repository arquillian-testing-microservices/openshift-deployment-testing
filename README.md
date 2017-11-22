# openshift-deployment-testing

The project aims at executing deployment tests using Arquillian Cube together with Fabric8 OpenShift Client for a 
WordPress and MySQL Application to ensure that the application will work when deployed into OpenShift cluster.

## Previous steps

* `git clone git@github.com:hemanik/openshift-deployment-testing.git`

* `minishift start` (tested with OpenShift Origin 3.6.0)

* `oc login`

## Run Tests

`mvn clean verify`
