const connection = require('../database/connection')

module.exports = {
    async index(req, res) {
        const data = await connection('places').select('*')
        return res.json({ "places": data })
    },

    async create(req, res) {
        const data = req.body
        const auth = req.headers.auth

        const result = await connection('places').insert(data)

        return res.json(data)
    },

    async delete(req, res) {
        const { place_id } = req.params
        const auth = req.headers.auth

        const place = await connection('places').select('created_by')
            .where('place_id', place_id)
            .first()

        if (place.created_by != auth) {
            return res.status(401).json({ error: 'Operation not allowed' })
        }

        await connection('places').delete().where('place_id', place_id)

        return res.status(204).send()
    }
}