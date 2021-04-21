const connection = require('../database/connection')

module.exports = {
    async index(req, res) {
        const data = await connection('friends').select('*')
        return res.json({ "friends": data })
    },

    async my_friends(req, res) {
        const { user_id } = req.params
        const data = await connection('friends as f')
            .select('f.*', 'u.first_name', 'u.last_name', 'u.email', 'u.city', 'u.state', 'u.country', 'u.img')
            .join('users as u', 'u.user_id', 'f.friend_id')
            .where({
                'f.user_id': user_id
            })
            .orWhere({
                'f.friend_id': user_id
            })
        return res.json({ "friends": data })
    },

    async acceptFriendRequest(req, res) {
        const data = req.body
        var timeInMs = Date.now();

        const friend = await connection('friends').select('*')
            .where({
                'user_id': data.user_id,
                'friend_id': data.friend_id
            }).first()


        if (friend == null) {
            return res.status(404).json({ error: "Friend request doesn't exists" })
        }

        if (friend.approved_date != null) {
            return res.status(401).json({ error: "Friend request already accpeted" })
        }

        await connection('friends')
            .update({
                "approved_date": timeInMs
            })
            .where({
                'user_id': data.user_id,
                'friend_id': data.friend_id
            })

        console.log("Friend accepted result")

        return res.json({ "success": "Friend request accepted" })
    },

    async create(req, res) {
        const data = req.body
        const auth = req.headers.auth

        const friend = await connection('friends').select('*')
            .where({
                'user_id': data.user_id,
                'friend_id': data.friend_id
            })
            .orWhere({
                'user_id': data.friend_id,
                'friend_id': data.user_id
            })
            .first()

        if (friend != null) {
            if (friend.approved_date == null) {
                return res.status(401).json({ error: 'Friend request already sent' })
            }
            else {
                return res.status(401).json({ error: "The user is already a friend, you can't send a request twice" })
            }
        }

        const result = await connection('friends').insert(data)

        console.log("New friend created")
        return res.json(data)
    },
}