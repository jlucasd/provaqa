#!/bin/bash


echo "Adicionando usuarios no Wildfly"
/opt/jboss/wildfly/bin/add-user.sh admin admin --silent
/opt/jboss/wildfly/bin/add-user.sh dev dev --silent

BIND=$(hostname -i)
BIND_OPTS="-Djboss.bind.address.management=0.0.0.0 -Djboss.bind.address.private=0.0.0.0 -Djgroups.join_timeout=1000 -Djgroups.bind_addr=$BIND -Djboss.bind.address=$BIND -Djboss.tx.node.id=$BIND"

/opt/jboss/wildfly/bin/standalone.sh -c standalone.xml $1 $BIND_OPTS $2