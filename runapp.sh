# Create mySQL schema
echo "---------------------------------------------"
echo "[INFO] Creating mySQL schema... "
echo "---------------------------------------------"

user=root
password=root
mysql --user="$user" --password="$password" --execute="CREATE DATABASE todo"

echo "---------------------------------------------"
echo " SUCCESFULL "
echo "---------------------------------------------"


# Springboot app
echo "---------------------------------------------"
echo "[INFO] Running spring-boot api... "
echo "---------------------------------------------"

cd backend
mvn spring-boot:run &


# React app
echo "---------------------------------------------"
echo "[INFO] Running react app... "
echo "---------------------------------------------"
cd .. 
cd frontend 
npm start