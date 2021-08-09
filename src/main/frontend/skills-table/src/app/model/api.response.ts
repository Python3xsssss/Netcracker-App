export class ApiResponse {
  httpStatusCode: number;
  status: string;
  message: string;
  result: any;

  constructor(httpStatusCode: number, status: string, message: string, result: any) {
    this.httpStatusCode = httpStatusCode;
    this.status = status;
    this.message = message;
    this.result = result;
  }
}
