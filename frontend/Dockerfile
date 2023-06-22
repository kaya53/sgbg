FROM node:alpine as builder

WORKDIR /app
# root directory SGBG
COPY ./frontend/package.json .
RUN npm install
COPY ./frontend .
RUN npm run build

FROM nginx:latest

COPY --from=builder /app/build /usr/share/nginx/html
