#!/usr/bin/env bash
set -euo pipefail

# Run helper para este projeto
# Uso:
#  ./run.sh build   -> compila o módulo (skip tests)
#  ./run.sh run     -> compila e roda o jar
#  ./run.sh run-boot-> roda via spring-boot:run

ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
JAVA_HOME_CANDIDATES=(/usr/lib/jvm/java-17-openjdk-amd64 /usr/lib/jvm/java-17-openjdk /usr/lib/jvm/java-17)

for candidate in "${JAVA_HOME_CANDIDATES[@]}"; do
  if [ -x "$candidate/bin/java" ]; then
    export JAVA_HOME="$candidate"
    export PATH="$JAVA_HOME/bin:$PATH"
    break
  fi
done

if [ -z "${JAVA_HOME-}" ]; then
  echo "WARN: JAVA_HOME não detectado automaticamente. Assegure Java 17 no PATH." >&2
fi

CMD=${1:-help}
case "$CMD" in
  build)
    mvn -f "$ROOT_DIR/health-track-api/pom.xml" clean package -DskipTests
    ;;
  run)
    mvn -f "$ROOT_DIR/health-track-api/pom.xml" clean package -DskipTests
    JAR="$ROOT_DIR/health-track-api/target/health-track-api-1.0.0.jar"
    if [ ! -f "$JAR" ]; then
      echo "Jar não encontrado: $JAR" >&2
      exit 1
    fi
    echo "Rodando: java -jar $JAR"
    java -jar "$JAR"
    ;;
  run-boot)
    mvn -f "$ROOT_DIR/health-track-api/pom.xml" spring-boot:run
    ;;
  help|*)
    echo "Uso: $0 {build|run|run-boot}"
    ;;
esac
