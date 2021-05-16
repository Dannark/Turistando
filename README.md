[![wakatime](https://wakatime.com/badge/github/Dannark/Turistando.svg)](https://wakatime.com/badge/github/Dannark/Turistando)

## Turistando
✨ A Travel Kotlin App using MVVM Archtecture, Room and others Design Patterns

![Arquitecture](https://developer.android.com/topic/libraries/architecture/images/final-architecture.png)

The app features includes:
1. Post and share Section
2. Explore New Places to visit
    - Recommended places 
    - Real places near by
    - Search Section
3. Users Profile

### Next comming updates:
- [x] Code Refactory (Recurrent)
- [ ] Includes Dagger dependences
- [ ] Firebase Login
- [ ] Realtime chat with websockets

### AWS Webserver API
The app uses a web server API build in node js hosted in AWS for testing purpose

The main objective with this app is to demonstrate how to develop and struct a good architecture with Android kotlin that is scalable and easy to maintain.

The server folder is on the root projet `nodejs_webserver` and you can start it by using the command `npm start`
You can also use the Online API instead of hosting on your localhost with the link:

`http://ec2-18-224-184-195.us-east-2.compute.amazonaws.com:3335/`

Endpoints are:
    - /places
    - /posts
    - /users

If you want to get the closest places near your location, you will also need to setup a `Google Places API` in your Google Developer Account in order to test that feature.
Just put yout api key on `PlacesApi` class

![turistando_app_01](https://user-images.githubusercontent.com/7622553/116609253-8caf5500-a90a-11eb-8f2b-d2393e886ace.gif)
