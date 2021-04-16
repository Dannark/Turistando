const connection = require('../database/connection')
const { index } = require('./PostController')

module.exports = {
    async index(req, res) {
        const data = await connection('places').select('*')
        return res.json({ "places": data })
    },

    async create(req, res) {
        const data = req.body
        const auth = req.headers.auth

        const result = await connection('places').insert(data)

        console.log('result.placeId=', result.placeId)
        return res.json(data)
    }
}