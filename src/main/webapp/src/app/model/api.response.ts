export class ApiResponse {
  status: number;
  message: number;
  result: any;

  constructor(status: number, message: number, result: any) {
    this.status = status;
    this.message = message;
    this.result = result;
  }
}
