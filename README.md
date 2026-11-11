## Digital Banking 

```
 A full stack project spring + Angular
 ```
 
### Bank Account Inheritance Strategy

The application uses the **Single Table Inheritance** strategy to store the different types of bank accounts (`CurrentAccount` and `SavingAccount`) in a single database table.

A discriminator column named `type` is used to identify the specific bank account type. This allows the application to distinguish between the different account types while keeping them in the same table.