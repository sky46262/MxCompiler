set -e
cd "$(dirname "$0")"
export RUN="java -classpath ./lib/antlr-runtime-4.7.1.jar:./bin com.company.Main"
$RUN