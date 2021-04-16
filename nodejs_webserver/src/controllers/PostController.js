const connection = require('../database/connection')

module.exports = {
    async index(req, res) {
        const posts = await connection('posts').select('*')
        return res.json({ "posts": posts })
    },

    async create(req, res) {
        const data = req.body

        await connection('posts').insert(data)

        return res.json(data)
    },

    async delete(req, res) {
        const { postId } = req.params
        const auth = req.headers.auth

        const post = await connection('posts').select('created_by')
            .where('postId', postId)
            .first()

        if (post.created_by != auth) {
            return res.status(401).json({ error: 'Operation not allowed' })
        }

        await connection('posts').delete().where('postId', postId)

        return res.status(204).send()
    }
}