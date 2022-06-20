package com.my_credit.util.list

inline fun <T, R> ArrayList<T>.arrayMap(transform: (T) -> R): ArrayList<R> {
    return mapTo(ArrayList(if (this is Collection<*>) this.size else 10), transform)
}