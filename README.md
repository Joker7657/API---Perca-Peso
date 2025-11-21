# Health Track API

Instruções rápidas para rodar o projeto localmente.

Pré-requisitos
- Java 17+
- Maven

Comandos úteis

- Build (sem testes):

```bash
./run.sh build
```

- Rodar o jar (após `build`):

```bash
./run.sh run
```

- Rodar via `spring-boot:run` (recompila e roda):

```bash
./run.sh run-boot
```

Observações
- O script tenta detectar `JAVA_HOME` em `/usr/lib/jvm/...`. Se seu Java estiver em outro lugar, exporte `JAVA_HOME` antes de rodar:

```bash
export JAVA_HOME=/caminho/para/java-17
export PATH="$JAVA_HOME/bin:$PATH"
```

- Logs do servidor (quando rodado manualmente com `java -jar`) serão emitidos no terminal.
- Caso queira rodar em background, use `nohup ./run.sh run > server.log 2>&1 &`.
