
exports.up = function (knex) {
    return knex.schema.createTable('places', (t) => {
        t.increments('placeId')
        t.bigInteger('creation_date').notNullable();
        t.bigInteger('created_by').notNullable();
        t.bigInteger('last_update_date').notNullable();
        t.string('place_name').notNullable();
        t.string('city').notNullable();
        t.string('state').notNullable();
        t.string('contry').notNullable();
        t.string('description').notNullable();
        t.string('img').notNullable();
        t.boolean('swimpool')
        t.boolean('buffet')
        t.boolean('bar')
    })
};

exports.down = function (knex) {
    return knex.schema.dropTable('places')
};
