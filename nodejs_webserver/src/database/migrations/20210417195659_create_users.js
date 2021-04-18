
exports.up = function (knex) {
    return knex.schema.createTable('users', (t) => {
        t.increments('user_id')
        t.bigInteger('creation_date').notNullable()
        t.bigInteger('last_update_date').notNullable()
        t.string('first_name').notNullable()
        t.string('last_name').notNullable()
        t.string('email').notNullable()
        t.string('city').notNullable()
        t.string('state').notNullable()
        t.string('country').notNullable()
        t.string('img').notNullable()
    })
};

exports.down = function (knex) {
    return knex.schema.dropTable('users')
};
