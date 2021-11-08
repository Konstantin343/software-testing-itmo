import sqlite3 from "sqlite3";

const db = new sqlite3.Database('../server/places.db')

export default async function clearDb() {
    await db.all('delete from liststousers')
    await db.all('delete from users')
    await db.all('delete from places')
    await db.all('delete from lists')
}