export interface Account {
  id? :string,
  balance? : number,
  createdAt? : string,
  type? : String,
  customerId? : number
}

export interface AccountList{
  accounts : Account[]
}
