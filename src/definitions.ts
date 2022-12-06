export interface EsimPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
