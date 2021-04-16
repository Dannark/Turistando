
exports.up = function (knex) {
    return knex.schema.createTable('posts', (t) => {
        t.increments('postId')
        t.bigInteger('creation_date').notNullable();
        t.bigInteger('created_by').notNullable();
        t.bigInteger('last_update_date').notNullable();
        t.string('title').notNullable()
        t.string('description').notNullable()
        t.int('likes').notNullable()
        t.string('img').notNullable();
    })
};

exports.down = function (knex) {
    return knex.schema.dropTable('posts')
};
