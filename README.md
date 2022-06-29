# ManUtd Backend

## Articles
| Methods | URLs                            |
|---------|---------------------------------|
| GET     | /api/v1/articles                |
| GET     | /api/v1/articles/{id}           |
| POST    | /api/v1/users/{userId}/articles |
| PUT     | /api/v1/articles/{id}           |
| DELETE  | /api/v1/articles/{id}           |

## Categories
| Methods | URLs                    |
|---------|-------------------------|
| GET     | /api/v1/categories      |
| GET     | /api/v1/categories/{id} |
| POST    | /api/v1/categories      |
| PUT     | /api/v1/categories/{id} |
| DELETE  | /api/v1/categories/{id} |

## Products
| Methods | URLs                                          |
|---------|-----------------------------------------------|
| GET     | /api/v1/products                              |
| GET     | /api/v1/categories/{categoryId}/products      |
| GET     | /api/v1/products/{id}                         |
| POST    | /api/v1/categories/{categoryId}/products      |
| PUT     | /api/v1/categories/{categoryId}/products/{id} |
| DELETE  | /api/v1/products/{id}                         |

## Sizes
| Methods | URLs                                   |
|---------|----------------------------------------|
| GET     | /api/v1/sizes                          |
| GET     | /api/v1/products/{productId}/sizes     |
| GET     | /api/v1/sizes/{id}                     |
| POST    | /api/v1/products/{productId}/sizes     |
| PUT     | /api/v1/sizes/{id} (Only update stock) |
| DELETE  | /api/v1/sizes/{id}                     |

## Orders
| Methods | URLs                                     |
|---------|------------------------------------------|
| GET     | /api/v1/orders                           |
| GET     | /api/v1/users/{userId}/orders            |
| GET     | /api/v1/orders/{id}                      |
| POST    | /api/v1/user/{userId}/orders             |
| PUT     | /api/v1/order/{id} (update status order) |

## Order Details
| Methods | URLs                                                 |
|---------|------------------------------------------------------|
| GET     | /api/v1/details                                      |
| GET     | /api/v1/orders/{orderId}/details                     |
| GET     | /api/v1/details/{id}                                 |
| POST    | /api/v1/user/orders/{orderId}/sizes/{sizeId}/details |