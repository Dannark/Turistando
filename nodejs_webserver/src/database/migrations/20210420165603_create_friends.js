
exports.up = function (knex) {
    return knex.schema.createTable('friends', (t) => {
        t.increments('friendship_id')

        t.bigInteger('user_id').notNullable()
        t.foreign('user_id').references('user_id').inTable('users')

        t.bigInteger('friend_id').notNullable()
        t.foreign('friend_id').references('user_id').inTable('users')

        t.bigInteger('creation_date').notNullable()
        t.bigInteger('approved_date')
    })
};

exports.down = function (knex) {
    return knex.schema.dropTable('friends')
};
