export interface EsimPlugin {
  isEnabled(): Promise<IeSimAvailable>;

  eSimAdd(code: IActivationCode): Promise<IeSimAvailable | IeSimStatus>
}

export interface IeSimAvailable {
  eSim: boolean;
  error: eSimError;
}

export interface eSimError {
  errorCode: number;
  errorMessage: string;
}

export interface IeSimStatus {
  success: string;
}

export interface IActivationCode {
  activationCode: string;
}