export class ResponseError extends Error {
    private response: Response;

    constructor(response: Response) {
        super(response.status+"");
        this.response = response;
    }

    public getResponse(): Response {
        return this.response;
    }
}