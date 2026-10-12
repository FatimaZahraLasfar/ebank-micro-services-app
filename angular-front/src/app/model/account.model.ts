export interface Account {
  id? :string,
  balance? : number,
  createdAt? : string,
  type? : String,
  customerId? : number
}
export enum RequestStatus {
  SUCCESS , ERROR
}
export interface AccountListState {
  accounts? : Account[]
  status? : RequestStatus,
  errorMessage? : string
}
