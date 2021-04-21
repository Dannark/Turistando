const express = require('express')
const postController = require('./controllers/PostController')
const placeController = require('./controllers/PlaceController')
const userController = require('./controllers/UserController')
const friendController = require('./controllers/FriendsController')

const routes = express.Router()

routes.post('/posts', postController.create)
routes.get('/posts', postController.index)
routes.delete('/posts/:post_id', postController.delete)

routes.post('/places', placeController.create)
routes.get('/places', placeController.index)
routes.delete('/places/:place_id', placeController.delete)

routes.post('/users', userController.create)
routes.get('/users', userController.index)
routes.get('/user/:user_id', userController.one)

routes.post('/friends', friendController.create)
routes.get('/friends', friendController.index)
routes.get('/friends/:user_id', friendController.my_friends)
routes.post('/friends/accept', friendController.acceptFriendRequest)

module.exports = routes