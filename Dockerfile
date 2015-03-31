FROM        ubuntu:14.04

MAINTAINER  ram natarajan <rnatarajan@user.com>

ENV         ACTIVATOR_VERSION 1.2.10
ENV         DEBIAN_FRONTEND noninteractive

# INSTALL OS DEPENDENCIES
RUN         apt-get update; apt-get install -y software-properties-common unzip

# INSTALL JAVA 7
RUN         echo debconf shared/accepted-oracle-license-v1-1 select true | debconf-set-selections && \
            echo debconf shared/accepted-oracle-license-v1-1 seen true | debconf-set-selections && \
            add-apt-repository -y ppa:webupd8team/java && \
            apt-get update && \
            apt-get install -y oracle-java7-installer

# INSTALL TYPESAFE ACTIVATOR
RUN         cd /tmp && \
            wget http://downloads.typesafe.com/typesafe-activator/$ACTIVATOR_VERSION/typesafe-activator-$ACTIVATOR_VERSION.zip && \
            unzip typesafe-activator-$ACTIVATOR_VERSION.zip -d /usr/local && \
            mv /usr/local/activator-$ACTIVATOR_VERSION /usr/local/activator && \
            rm typesafe-activator-$ACTIVATOR_VERSION.zip
			
RUN 	sudo mkdir -p /opt/docker/logs

RUN 	sudo ls -alrt /opt/docker/logs
COPY         web/app /opt/docker/web/app
COPY         web/test /opt/docker/web/test
COPY         web/conf /opt/docker/web/conf
COPY         web/public /opt/docker/web/public
COPY         web/build.sbt /opt/docker/web/
COPY         web/project/plugins.sbt /opt/docker/web/project/
COPY         web/project/build.properties /opt/docker/web/project/
COPY		 web/activator* /opt/docker/web/
COPY			services/* /opt/docker/services/

WORKDIR /opt/docker/web/




RUN echo =========================================================================
RUN        cd /opt/docker/web/; /usr/local/activator/activator clean  docker:stage

RUN echo =========================================================================
RUN pwd
volume = "ls -alrt /opt/docker/services/"

RUN echo =========================================================================
RUN echo $volume
RUN echo =========================================================================
##RUN         rm /build/docker/target/universal/stage/bin/*.bat