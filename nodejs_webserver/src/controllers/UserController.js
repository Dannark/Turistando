const connection = require('../database/connection')

module.exports = {
    async index(req, res) {
        const data = await connection('users').select('*')
        return res.json({ "users": data })
    },

    async one(req, res) {
        const { user_id } = req.params
        const data = await connection('users').select('*')
            .where('user_id', user_id)
        return res.json({ "users": data })
    },

    async create(req, res) {
        const data = req.body
        const auth = req.headers.auth

        const user = await connection('users').select('*')
            .where({
                'email': data.email,
            }).first()

        if (user != null) {
            return res.status(401).json({ error: "The user is already exists" })
        }


        const result = await connection('users').insert(data)

        console.log("New user created")
        return res.json(result)
    },
}