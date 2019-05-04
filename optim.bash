set -e
cd "$(dirname "$0")"
export RUN="java -classpath ./lib/antlr4-runtime-4.7.2.jar:./bin com.company.Main"
$RUN