FROM openjdk-8-alpine

ENV LANG C.UTF-8

EXPOSE 8090

RUN mkdir -p /root/test-storage

COPY . /root/test-storage

WORKDIR /root/test-storage

RUN chmod +x /root/test-storage/entry_point.sh

