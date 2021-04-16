const express = require('express')
const postController = require('./controllers/PostController')
const placeController = require('./controllers/PlaceController')

const routes = express.Router()

routes.post('/posts', postController.create)
routes.get('/posts', postController.index)
routes.delete('/posts/:postId', postController.delete)

routes.post('/places', placeController.create)
routes.get('/places', placeController.index)

module.exports = routes