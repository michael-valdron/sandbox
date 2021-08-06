FROM openjdk:8-alpine

ARG user
ARG password
ARG protocol=free
ARG version=0.9.5697
ARG data_dir=/data
ARG log_dir=/log

ENV INSTALLATION_PATH=/datomic
ENV PACKAGE_NAME=datomic-${protocol}-${version}
ENV TRANSACTOR_PATH=config/transactor.properties

# Install shell, download, and unpackage tools
RUN apk add --no-cache wget unzip bash

# Create installation folder for Datomic
RUN mkdir ${INSTALLATION_PATH}

# Create data directory
RUN mkdir ${data_dir}

# Create log directory
RUN mkdir ${log_dir}

# Set installation folder as working directory
WORKDIR ${INSTALLATION_PATH}

# Download Datomic using MyDatomic credentials
RUN wget --http-user=${user} --http-password=${password} \
https://my.datomic.com/downloads/${protocol}/${version} \
-O ${PACKAGE_NAME}.zip

# Unpackage Datomic
RUN unzip ${PACKAGE_NAME}.zip && rm ${PACKAGE_NAME}.zip

# Move unpackaged directories and files into installation folder and remove unpackaged location
RUN mv ${PACKAGE_NAME}/* ./ && rm -rf ${PACKAGE_NAME}

# Create transactor config
RUN touch ${TRANSACTOR_PATH}

# Create start script
RUN echo -e "#!/bin/bash\n./bin/transactor ${TRANSACTOR_PATH}" > start.sh

# Run transactor for local development
CMD ["bash", "start.sh"]
