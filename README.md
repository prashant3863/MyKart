MyKart Backend
==============

MyKart Server serves as the backend for MyKart Android Client.

Development Instructions
------------------------
  - Install `ruby 2.2.0`
  - Clone MyKart repository
  - cd `MyKart/mykart_server`
  - Run `bundle install`
  - Install Spree by running `bundle exec rails g spree:install`
  - Install the migrations by running `bundle exec rake railties:install:migrations`
  - run `bundle exec rake db:migrate`
  - Run the rails server by executing `bundle exec rails s`

Note: If you need to use the local server for MyKart Development, run the rails server
using
`bundle exec rails s -b 0.0.0.0`

This will bind the rails server to IP Address 0.0.0.0
