package yaml

import (
	"os"

	"gopkg.in/yaml.v3"
)

func parse(filename string) (*yaml.Node, error) {
	var documentNode yaml.Node
	content, err := os.ReadFile(filename)
	if err != nil {
		return nil, err
	}

	err = yaml.Unmarshal(content, &documentNode)
	return &documentNode, err
}
