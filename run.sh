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

# If JAVA_HOME still not set, try to detect via `which java` and resolve its parent dirs
if [ -z "${JAVA_HOME-}" ]; then
  if command -v java >/dev/null 2>&1; then
    java_cmd=$(readlink -f "$(command -v java)")
    # java_cmd -> .../bin/java, so parent dir twice is JAVA_HOME
    java_parent=$(dirname "$(dirname "$java_cmd")")
    if [ -x "$java_parent/bin/java" ]; then
      export JAVA_HOME="$java_parent"
      export PATH="$JAVA_HOME/bin:$PATH"
    fi
  fi
fi

function java_major_version() {
  local java_cmd
  if command -v java >/dev/null 2>&1; then
    java_cmd="$(command -v java)"
  elif [ -n "${JAVA_HOME-}" ] && [ -x "${JAVA_HOME}/bin/java" ]; then
    java_cmd="${JAVA_HOME}/bin/java"
  else
    echo "0"
    return
  fi
  # Capture output like 'openjdk version "17.0.1"'
  ver=$($java_cmd -version 2>&1 | head -n1)
  # Extract major version robustly (handles formats like: openjdk version "17.0.16" ...)
  maj=$(echo "$ver" | sed -E 's/.*version "([0-9]+).*".*/\1/' | cut -d'.' -f1)
  if [[ "$maj" =~ ^[0-9]+$ ]]; then
    echo "$maj"
    return
  fi
  echo "0"
}

JAVA_MAJOR=$(java_major_version)
if [ "$JAVA_MAJOR" -lt 17 ]; then
  echo "ERRO: Java 17 ou superior é necessário. Versão detectada: $JAVA_MAJOR" >&2
  echo "Instale/ative Java 17 e rode novamente. Exemplo temporário:" >&2
  echo "  export JAVA_HOME=/usr/lib/jvm/java-17-openjdk-amd64 && export PATH=\"$JAVA_HOME/bin:$PATH\"" >&2
  exit 1
fi

if ! command -v mvn >/dev/null 2>&1; then
  echo "ERRO: Maven não encontrado. Instale o Maven (ex: apt install maven)" >&2
  exit 1
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
