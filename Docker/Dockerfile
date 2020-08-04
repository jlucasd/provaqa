FROM jboss/wildfly:19.0.0.Final
USER root
COPY docker-entrypoint.sh /tmp/docker-entrypoint.sh
RUN chmod +x /tmp/docker-entrypoint.sh
ENV TZ=America/Sao_Paulo
ENV LANG pt_BR.UTF-8
ENV LANGUAGE pt_BR:pt
ENV LC_ALL pt_BR.UTF-8
#COPY wildfly/modules $JBOSS_HOME/modules/
COPY standalone.xml $JBOSS_HOME/standalone/configuration/standalone.xml

COPY libs/ ${JBOSS_HOME}/modules/system/layers/base/

ADD prova-java.war $JBOSS_HOME/standalone/deployments/prova-java.war
USER jboss
ENTRYPOINT ["/tmp/docker-entrypoint.sh"]