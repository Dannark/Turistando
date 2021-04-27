
exports.up = function (knex) {
    return knex.schema.createTable('places', (t) => {
        t.increments('place_id')
        t.bigInteger('creation_date').notNullable()
        t.bigInteger('created_by').notNullable()
        t.bigInteger('last_update_date').notNullable()
        t.string('place_name').notNullable()
        t.string('city').notNullable()
        t.string('state').notNullable()
        t.string('country').notNullable()
        t.string('description').notNullable()
        t.string('img')
        t.binary('img_bitmap')
        t.string('attributions').notNullable()
        t.decimal('rating')
        t.integer('user_ratings_total')
        t.integer('price_level')
        t.string('business_status')
        t.string('address')
        t.string('place_key')
    })
};

exports.down = function (knex) {
    return knex.schema.dropTable('places')
};
