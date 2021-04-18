const express = require('express')
const postController = require('./controllers/PostController')
const placeController = require('./controllers/PlaceController')
const userController = require('./controllers/UserController')

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

module.exports = routes