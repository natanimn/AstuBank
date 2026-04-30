set -e

RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m'

log()    { echo -e "${BLUE}[INFO]${NC}  $1"; }
success(){ echo -e "${GREEN}[OK]${NC}    $1"; }
warn()   { echo -e "${YELLOW}[WARN]${NC}  $1"; }
error()  { echo -e "${RED}[ERROR]${NC} $1"; exit 1; }

check_docker() {
  if ! command -v docker &>/dev/null; then
    error "Docker is not installed. Visit https://docs.docker.com/get-docker/"
  fi

  if ! docker info &>/dev/null; then
    error "Docker daemon is not running. Please start Docker and try again."
  fi

  success "Docker is running."
}

start_compose() {
  log "Starting services with Docker Compose..."

  DISPLAY=$DISPLAY docker compose up --build "$@"
}

start_compose() {
  log "Starting backend services with Docker Compose..."
  docker compose up --build -d
  success "Backend services started."
}

check_java() {
  if ! command -v java &>/dev/null; then
    error "Java is not installed. Please install Java 21. Visit https://adoptium.net/"
  fi
  success "Java is available."
}

start_desktop() {
  log "Building desktop JAR using Docker..."
  docker build -f desktop/Dockerfile --target build-desktop -t desktop-build .
  docker create --name desktop-extract desktop-build
  docker cp desktop-extract:/build/desktop/target/desktop-1.0.jar ./desktop.jar
  docker rm desktop-extract
  log "Launching desktop..."
  java -jar desktop.jar
}

cleanup() {
  log "Shutting down..."
  docker compose down
  rm -f desktop.jar
}

trap cleanup EXIT

echo ""
echo -e "${BLUE}=============================${NC}"
echo -e "${BLUE}   Starting ASTU Bank       ${NC}"
echo -e "${BLUE}=============================${NC}"
echo ""

check_docker
check_java
start_compose "$@"
start_desktop
