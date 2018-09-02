SOURCE="${BASH_SOURCE[0]}"
while [ -h "$SOURCE" ]; do # resolve all symlinks
  DIR="$( cd -P "$( dirname "$SOURCE" )" && pwd )"
  SOURCE="$(readlink "$SOURCE")"
  [[ $SOURCE != /* ]] && SOURCE="$DIR/$SOURCE" # resolve relative symlinks
done
ABS_PATH="$( cd -P "$( dirname "$SOURCE" )" && pwd )"

java -cp ${ABS_PATH}/target/ttl-convert-1.0-SNAPSHOT-jar-with-dependencies.jar scicrunch.App $@
