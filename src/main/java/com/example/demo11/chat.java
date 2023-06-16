package com.example.demo11;

abstract class Chat implements QueryResolutionStrategy {
    QueryResolutionResult result;
    Boolean engels = false;


    Chat() {

    }
}

          class ChatFilms extends Chat {
              ChatFilms() {
                  // Constructor implementation
              }

              @Override
              public QueryResolutionResult resolve(QueryResolutionForm ontvangQuery) {

                  if (ontvangQuery.getQueryData().equals("Samuel Jackson")) {


                          this.result = new QueryResolutionResult("Jules. Samuel Jackson played Jules in Pulp Fiction");
                          return result;


                      }


                  if (ontvangQuery.getQueryData().equals("Uma Thurman")) {

                          this.result = new QueryResolutionResult("The Bride. Uma Thurman plays the bride in Kill Bill");
                          return result;

                  }

                  if (ontvangQuery.getQueryData().equals("Tarentino")) {
                          this.result = new QueryResolutionResult("Quinten Tarentino is one of the most famous and talented movie directors in the world.");
                          return result;
                      }
                  return result = null;
                  }

              public String gimmeResultsI() {

                  return result.getData();
              }
          }



    class ChatBoeken extends Chat {

        ChatBoeken() {

        }

        @Override
        public QueryResolutionResult resolve(QueryResolutionForm ontvangQuery) {

            if (ontvangQuery.equals("Ayn Rand")) {
                QueryResolutionResult sss = new QueryResolutionResult("Objectivism");
                return sss;
            }
            if (ontvangQuery.equals("JRR Tolkien")) {
                QueryResolutionResult sss = new QueryResolutionResult("Harry Potter");
                return sss;
            }

            if (ontvangQuery.equals("Aldus Huxley")) {
                QueryResolutionResult sss = new QueryResolutionResult("Gimme some Soma.");
                return sss;
            }

            return null;
        }
    }
