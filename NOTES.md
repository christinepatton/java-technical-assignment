# Notes

Please add here any notes, assumptions and design decisions that might help us understand your thought process.

I don't like how tightly coupled the various Product and Item classes are. I note, for example, that the Item classes aren't even tested directly, only via the Product tests. If I had more time I would take a much deeper look at these classes and likely do a more thorough refactor on them.

The one discount class I wrote only applies discounts to single products. It has no concept of applying discounts to groups of products. This is arguably sufficient for the discount I chose (buy one get one free), but would not work for discounts applied to categories of items such as vegetables.

I added some functionality to BasketTest, but that functionality is a little bit untidy. The way I modeled discounts relies on products being singletons, which the test does not currently implement. If I had more time I would refactor this test to deal with this.