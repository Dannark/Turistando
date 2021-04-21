const connection = require('../database/connection')

module.exports = {
    async index(req, res) {
        const posts = await connection('posts')
            .join('users', 'created_by', 'users.user_id')
            .select('posts.*', 'users.first_name', 'users.img as user_img', 'users.country')

        return res.json({ "posts": posts })
    },

    async create(req, res) {
        const data = req.body

        await connection('posts').insert(data)

        return res.json(data)
    },

    async delete(req, res) {
        const { post_id } = req.params
        const auth = req.headers.auth

        const post = await connection('posts').select('created_by')
            .where('post_id', post_id)
            .first()

        if (post.created_by != auth) {
            return res.status(401).json({ error: 'Operation not allowed' })
        }

        await connection('posts').delete().where('post_id', post_id)

        console.log("New post created")
        return res.status(204).send()
    }
}