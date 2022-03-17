# ATM-card-form
without any third party library mask atm card form
no need to signin
card number masking
master card
visa
american express
discover
and so on
full validation and submittion without any gateway

You are asked to create an Android "screen" that would allow a user to enter and submit
the following data from an American Express, Discover, MasterCard, or Visa credit card:
● Credit card number
● Expiration date (MM/YY format)
● CVV security code
● First name
● Last name
● ZIP Code
● Country
If any of the entered data was invalid, the user needed to be notified so they could
correct it. In addition to not having issues like empty fields or a badly-formed or expired
expiration date, credit card data also had to meet these criteria to be considered valid:
● Credit card numbers needed to be in valid American Express, Discover,
MasterCard, or Visa format according to the specs listed below:
○ The first digit and total length is different for each card network:
■ Visa cards – Begin with a 4 and have 13 or 16 digits
■ Mastercard cards – Begin with a 5 and has 16 digits
■ American Express cards – Begin with a 3, followed by a 4 or a 7
has 15 digits
■ Discover cards – Begin with a 6 and have 16 digits
● Credit card numbers also needed to pass Luhn validation - see
https://en.wikipedia.org/wiki/Luhn_algorithm
● The CVV had to meet these criteria: https://www.cvvnumber.com/cvv.html
● Valid expiry date format is “MM/YY” and “/” should be appended automatically
after MM.
● First and last names could only contain characters that were "alphabetical and
spaces". (Many people have hyphens in their names as well, you may ignore it
for this assignment.)
● Zip code should be numeric
● Countries should be in dropdown with search enabled. Hardcode 15-20 countries
list (Note: You cannot use any third party SDK or library for countries, dropdown
and search)
When the user submitted valid credit card data, the app should pop up an alert dialog
notifying them that the payment was successful. If the credit card number or expiry date
is invalid, the submit button should be disabled.
You are suggested to include a README/text file about your approach and reasoning
when designing and creating the app.
What we want:
● Clean, tested and readable code
● Implement using an architectural design pattern of your choice.
● Demonstrated knowledge of MVC, delegation, UI and navigation, fetching and
parsing JSON data, git, dependency management*
NOTES:
● If you choose another way of getting information from a child
VC/Activity/Fragment to a parent than through delegation/observables (e.g. some
reactive framework or something else) then don't worry about demonstrating
knowledge about delegation.
● If you choose not to use any third party dependencies, but do everything on your
own, don't worry about dependency management.
● Use 3rd party libraries if you have to, but be prepared to argue why that library
was necessary.
● No need to use any payment gateway
