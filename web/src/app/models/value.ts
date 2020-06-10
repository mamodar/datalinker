/**
 * This class represents a value as received by the API calls.
 * @author Kyanoush Yahosseini
 */
export class Value {
  constructor(value: Value) {
    this.id = value.id;
    this.projectId = value.projectId;
    this.questionText = value.questionText;
    this._answerText = value.answerText;
  }


  id: number;
  projectId: number;
  attribute: number;
  questionText: string;
  // tslint:disable-next-line:variable-name
  private readonly _answerText: string;

  get answerText(): string {
    if (this._answerText === '1') {
      return 'ja';
    }
    if (this._answerText === '0') {
      return 'nein';
    }
    if (Date.parse(this._answerText)) {
      const date: Date = new Date(this._answerText);
      return date.toLocaleDateString();
    }
    return this._answerText;
  }

}
