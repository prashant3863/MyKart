MyKart Ecommerce Application
==========================

MyKart App is an Android Client for MyKart backend.

Development Instructions
------------------------
  - Clone MyKart repository
  - Open `MyKart/android` in Android Studio
  - Build MyKart Android Client
  - Run the app

Note: If you want to use the local server for development, change the network strings
with the reference to the following snippet:

File to change: app/src/main/res/values/network.xml

```
  host: <your_ip_address>
  port: <port_of_rails_server>
  default_api_key: <generated_api_key_for_unregistered_user>
```