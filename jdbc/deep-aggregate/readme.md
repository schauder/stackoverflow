# Demonstrator and issue reproducer for nested collections

This is inspired by and referenced in https://stackoverflow.com/q/9353167/66686
There are three variants of the same problem in three subdirectories.

The `list` directory constructs and stores an aggregate with two levels of nested entity collections.

Note that both  levels use `List` as the collection type. `Map` should work the same way.

`Set` does not work for the first level, because in order for the third level to reference the second level, the second level does need a proper primary key, which is constructed from the back reference to the top level (`BOOK_ID`) and the index of the array.

The second level does not use `Set` because there seems to be a bug, which results in the back reference _loosing_ the index/key column from the second level.


The directory `set-with-id` demonstrates a different approach, where a `Set` is used on both levels.
For this to work, the second level needs a proper standalone id, annotated as such. This column becomes the sole primary key column in the table and therefore its values must be unique across all `BookCopy` instances.

The directory `list-with-set-reproducer` demonstrates a bug where a `Set` referenced from a `List` results in wrong SQL generation.